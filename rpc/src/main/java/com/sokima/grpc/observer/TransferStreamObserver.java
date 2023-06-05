package com.sokima.grpc.observer;

import com.sokima.grpc.persistent.AccountDatabase;
import com.sokima.grpc.service.Account;
import com.sokima.grpc.service.TransferRequest;
import com.sokima.grpc.service.TransferResponse;
import com.sokima.grpc.service.TransferStatus;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class TransferStreamObserver implements StreamObserver<TransferRequest> {

    private StreamObserver<TransferResponse> responseStreamObserver;

    public TransferStreamObserver(StreamObserver<TransferResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccount = transferRequest.getFromAccount();
        int toAccount = transferRequest.getToAccount();
        int amount = transferRequest.getAmount();
        int balance = AccountDatabase.getBalance(fromAccount);
        TransferStatus responseStatus = TransferStatus.FAILED;

        if (balance >= amount && fromAccount != toAccount) {
            AccountDatabase.deductBalanceAndReturn(fromAccount, amount);
            AccountDatabase.addBalanceAndReturn(toAccount, amount);
            responseStatus = TransferStatus.SUCCESS;
        }

        TransferResponse response = TransferResponse.newBuilder()
                .setStatus(responseStatus)
                .addAccounts(
                        Account.newBuilder()
                                .setAccountNumber(fromAccount)
                                .setAmount(AccountDatabase.getBalance(fromAccount))
                                .build()
                )
                .addAccounts(
                        Account.newBuilder()
                                .setAccountNumber(toAccount)
                                .setAmount(AccountDatabase.getBalance(toAccount))
                                .build()
                )
                .build();

        responseStreamObserver.onNext(response);
    }

    @Override
    public void onError(Throwable throwable) {
        throw Status.fromThrowable(throwable).asRuntimeException();
    }

    @Override
    public void onCompleted() {
        responseStreamObserver.onCompleted();
    }
}

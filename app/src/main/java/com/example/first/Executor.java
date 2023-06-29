package com.example.first;

import android.widget.EditText;

import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executor {
    EditText user,password;
    Connection connection;
    ExecutorService executer= Executors.newSingleThreadExecutor();
    Executor(EditText user, EditText password, Connection connection) {
    this.user=user;
    this.password=password;
    this.connection=connection;
    }
    Callable<Integer> task=new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
            UserCheck u1=new UserCheck(user,password,connection);
            int k=u1.check();
            return k;
        }
    };
    int perform_execute() throws ExecutionException, InterruptedException {
        Future<Integer> future=executer.submit(task);
        return future.get();
    }
}

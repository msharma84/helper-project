package org.example.streams.validator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrationServiceTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        RegistrationService registrationService = new RegistrationService(
                email -> email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$"),
                pwd -> pwd != null && pwd.length() >= 8
        );

        User user1 = new User("Rahul","rahul@gmail.","abc1234556666");
        User user2 = new User("Mohit","mohitsharma603@gmail.com","abc@12345678");
        User user3 = new User("Sachin","sachin@gmail.com","abc");

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<String> future1 =CompletableFuture.supplyAsync(()->registrationService.register(user1),
                executorService).exceptionally(Throwable::getMessage);

        CompletableFuture<String> future2 =CompletableFuture.supplyAsync(()->registrationService.register(user2),
                executorService).exceptionally(Throwable::getMessage);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> registrationService.register(user3),
                executorService).exceptionally(Throwable::getMessage);

        CompletableFuture.allOf(future1,future2,future3).join();
        String result1 = future1.get();
        String result2 = future2.get();
        String result3 = future3.get();

        System.out.println("User 1 registration result : "+result1);
        System.out.println("User 2 registration result : "+result2);
        System.out.println("User 3 registration result : "+result3);

        executorService.shutdown();
    }
}

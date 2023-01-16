package io.study.modernjavainaction.ch16;

import java.util.concurrent.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;

public class FutureTest1 {

	public double getPrice(String product){
		return calculatePrice(product);
	}

	public double calculatePrice(String product){
		delay();
		Random random = new Random();
		return random.nextDouble() + product.charAt(0) + product.charAt(1);
	}

	public static void delay(){
		try{
			Thread.sleep(1000L);
		}
		catch (InterruptedException e){
			throw new RuntimeException(e);
		}
	}

	public Future<Double> getPriceAsync(String product){
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(()->{
			try{
				double price = calculatePrice(product);
				futurePrice.complete(price);
			}
			catch(Exception ex){
				futurePrice.completeExceptionally(ex);
			}
		}).start();
		return futurePrice;
	}

	@Test
	@DisplayName("비동기가 적용되지 않는 예제")
	void testFuturePrice(){
		long start = System.nanoTime();
		double price = getPrice("my product 1");
		long executionTime = ((System.nanoTime() - start)/1000000);
		System.out.println("price = " + price + ", execution time = " + executionTime + " msecs");
	}

	public void doSomethingElse(){
		System.out.println("*** doSomethingElse (begin)***");
		System.out.println("=> 프로필 이미지 조회");
		System.out.println("==> 사용자 취향 분석 API 호출");
		System.out.println("===> 마일리지 적립 API 호출");
		System.out.println("*** doSomethingElse (end)***");
		System.out.println();
	}

	@Test
	@DisplayName("비동기를 CompletableFuture를 이용해 적용한 예제")
	void testCompletableFuturePrice(){
		long start = System.nanoTime();
		Future<Double> futurePrice = getPriceAsync("my product2");

		long invocationTime = ((System.nanoTime() - start) / 1000000);
		System.out.println("[시간체크] getPriceAsync() 를 단순 호출하는 데에만 걸린 시간 = " + invocationTime + " msecs");
		System.out.println();

		doSomethingElse(); // 여러가지 다른 작업들을 수행
		try{
			// 가격 정보가 있으면 Future 에서 가격정보를 읽고, 가격 정보가 없으면 가격 정보를 받을 때까지 블록한다.
			double price = futurePrice.get();
			System.out.printf("Price is %.2f %n", price);
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		long retrievalTime = ((System.nanoTime() - start)/1000000);
		System.out.println("[시간체크] Price was returned finally after " + retrievalTime + " msecs");
	}
}

package io.study.modernjavainaction.ch16;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class FutureTest2 {
	class Shop{
		String productName;
		String name;

		public Shop(String name){
			this.name = name;
		}

		public double getPrice(String product){
			return calculatePrice(product);
		}

		public double calculatePrice(String product){
			delay();
			Random random = new Random();
			return random.nextDouble() + product.charAt(0) + product.charAt(1);
		}

		public void delay(){
			try{
				Thread.sleep(1000L);
			}
			catch (InterruptedException e){
				throw new RuntimeException(e);
			}
		}

		public String getName() {
			return name;
		}
	}

	List<Shop> shops = Arrays.asList(
							new Shop("BastPrice"),
							new Shop("LetsSaveBig"),
							new Shop("MyFavoriteShop"),
							new Shop("BuyItAll"),
							new Shop("Superman"));

	public List<String> findPrices(String product){
		return shops.stream()
			.map(shop -> {
				return String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)); })
			.collect(Collectors.toList());
	}

	@Test
	void 테스트1_동기식으로_모든_상점의_가격을_단순하게_나열해서_풀어내기(){
		long start = System.nanoTime();
		List<String> prices = findPrices("내 핸드폰이에요~!!");
		prices.forEach(System.out::println);

		long duration = (System.nanoTime() - start) / 1000000;

		System.out.println();
		System.out.println("[시간측정] Done in " + duration + " msecs");
	}

	public List<String> findPricesByParallel(String product){
		return shops.parallelStream()
			.map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
			.collect(Collectors.toList());
	}

	@Test
	void 테스트2_동기식으로_구하는_5개_상점의_가격을_각각의_CPU에_분산시켜서_처리해보기(){
		long start = System.nanoTime();
		List<String> prices = findPricesByParallel("내 핸드폰이에요~!!");
		prices.forEach(System.out::println);

		long duration = (System.nanoTime() - start) / 1000000;

		System.out.println();
		System.out.println("[시간측정] Done in " + duration + " msecs");
	}

	public void doSomethingElse(){
		System.out.println("*** doSomethingElse (begin)***");
		System.out.println("=> 프로필 이미지 조회");
		System.out.println("==> 사용자 취향 분석 API 호출");
		System.out.println("===> 마일리지 적립 API 호출");
		System.out.println("*** doSomethingElse (end)***");
		System.out.println();
	}

	public List<String> findPricesByCompletableFuture(String product){
		System.out.println(">>> findPricesByCompletableFuture(String product)");
		List<CompletableFuture<String>> priceFutures =
			shops.stream()
				.map(shop -> {
					// CompletableFuture.supplyAsync : CompletableFuture 로 각각의 가격을 비동기적으로 계산한다.
					return CompletableFuture.supplyAsync(() -> {
						return shop.getName() + "price is " + shop.getPrice(product);
					});
				})
				.collect(Collectors.toList());

		doSomethingElse();

		return priceFutures.stream()
				// CompletableFuture::join : 모든 비동기 동작이 끝나길 기다린다.
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
	}

	@Test
	void 테스트3_비동기식으로_5개_상점의_가격을_구해보기(){
		long start = System.nanoTime();
		List<String> prices = findPricesByCompletableFuture("내 핸드폰이에요~!!");
		prices.forEach(System.out::println);

		long duration = (System.nanoTime() - start) / 1000000;

		System.out.println();
		System.out.println("[시간측정] Done in " + duration + " msecs");
	}
}

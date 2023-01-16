package io.study.modernjavainaction.ch16.shop;

import static io.study.modernjavainaction.ch16.util.Util.delay;
import static io.study.modernjavainaction.ch16.util.Util.format;

import io.study.modernjavainaction.ch16.discount.Discount;
import java.util.Random;

public class Shop {
	private final String name;
	private final Random random;

	public Shop(String name) {
		this.name = name;
		random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
	}

	public String getPrice(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return name + ":" + price + ":" + code;
	}

	public double getPriceDouble(String product){
		return calculatePrice(product);
	}


	public double calculatePrice(String product) {
		// static import
		delay();
		// static import
		return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
	}

	public String getName() {
		return name;
	}
}

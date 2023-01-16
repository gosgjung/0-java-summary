package io.study.modernjavainaction.ch3.ch3_4_built_in_functional_interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

class Predicate_T {

	/**
	 * 단순 사용예제 > 공백문자열 여부 체크
	 */
	@Test
	void testPredicate_T_1() {
		Predicate<String> isWhiteSpace = (t) -> {
			boolean b = t.length() == 0;
			return b;
		};

		String whiteSpace = "";
		boolean whiteSpaceFlag = isWhiteSpace.test(whiteSpace);
		System.out.println("문자열 '" + whiteSpace + "' 는 공백문자인가요? " + whiteSpaceFlag);
	}

	enum DeviceType {
		CPU(100, "CPU"){},
		RAM(200, "RAM"){},
		DISK(500, "DISK"){};

		private int deviceTypeCode;
		private String deviceTypeNm;

		DeviceType(int deviceTypeCode, String deviceTypeNm){
			this.deviceTypeCode = deviceTypeCode;
			this.deviceTypeNm = deviceTypeNm;
		}
	}

	/**
	 * 리스트 내에서 추출해낼 조건 검사식 predicate 에 일치하는 요소들만을 추려낸 새로운 리스트를 반환한다.
	 * @param input
	 * @param predicate
	 * @param <T>
	 * @return
	 */
	public <T> List<T> filter (List<T> input, Predicate<T> predicate){
		List<T> result = new ArrayList<>();

		for(T t : input){
			if(predicate.test(t)){
				result.add(t);
			}
		}

		return result;
	}

	/**
	 * Predicate<T> 를 파라미터로 전달받는 예제
	 */
	@Test
	void testPredicate_T_2(){
		EnumSet<DeviceType> es = EnumSet.of(DeviceType.CPU, DeviceType.RAM, DeviceType.DISK);
		List<Object> deviceList = Arrays.asList(es.toArray());
		List<Object> d = filter(deviceList, t -> {
			return (DeviceType.CPU.equals(t));
		});

		System.out.println("d :: " + d);
	}

}

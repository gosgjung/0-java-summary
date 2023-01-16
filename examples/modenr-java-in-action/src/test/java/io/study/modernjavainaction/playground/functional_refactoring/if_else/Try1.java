package io.study.modernjavainaction.playground.functional_refactoring.if_else;

import java.util.function.BiFunction;

public class Try1 {

	interface SerialGenerator {
		public int apply(int row, int col, int col_size);
	}

	interface BoundaryValidator{
		public boolean check(int row, int col, int col_size, String [][] array);
	}

	SerialGenerator generator = (row, col, col_size) -> {
		return (row * col_size) + col;
	};

	static class Position{
		int row;
		int col;
		int col_size;

		private static final SerialGenerator SERIAL_GENERATOR = ((row, col, col_size) -> {
			return (row * col_size) + col;
		});

		public Position(int row, int col){
			this.row = row;
			this.col = col;
		}

		public Position(int row, int col, int col_size){
			this.row = row;
			this.col = col;
			this.col_size = col_size;
		}

		public int getSerialNumber(){
			return SERIAL_GENERATOR.apply(row, col, col_size);
		}

		public boolean boundaryCheck(){
			// TODO
			return false;
		}

		public boolean islandCheck(){
			// TODO
			return false;
		}

		public boolean isAvailableToGo(){
			return boundaryCheck() && islandCheck();
		}

	}

	/**
	 * 싱글턴 (Bill Pugh's Singleton)
	 */
	static class Offset{

		public static Position top(){return InnerOffset.TOP;}
		public static Position bottom(){return InnerOffset.BOTTOM;}
		public static Position left(){return InnerOffset.LEFT;}
		public static Position right(){return InnerOffset.RIGHT;}

		static class InnerOffset{
			/**
			 * TODO :: 이부분 동적으로 생성할 만한 방법 있을지 생각해보기
			* */
			private static final BoundaryValidator topValidator = (row, col, col_size, array)->{
				if(row+1 >=0 && array[row][col] == "1")	return true;
				return false;
			};

			private static final BoundaryValidator bottomValidator = (row, col, col_size, array)->{
				if(row >=0 && array[row][col] == "1")	return true;
				return false;
			};

			// ...

			private static final Position TOP 		= new Position(-1, 0, 3);
			private static final Position BOTTOM 	= new Position(1, 0, 3);
			private static final Position LEFT 		= new Position(0, -1, 3);
			private static final Position RIGHT 	= new Position(0, 1, 3);
		}
	}

	public void solution1(int row_size, int col_size, String [][] array){
		int row = 1; int col = 1;

		final String ISLAND = "1";
		final String WATER 	= "0";

		// 상
		if(row-1 >= 0 && array[row-1][col] == ISLAND){
			System.out.println("위에 노드를 방문했는데 육지에요.");
		}
		// 하
		if(row+1 <3 && array[row+1][col] == ISLAND){
			System.out.println("아래 노드를 방문했는데 육지에요.");
		}
		// 좌
		if(col-1 >=0 && array[row][col-1] == ISLAND){
			System.out.println("아래 노드를 방문했는데 육지에요.");
		}
		// 우
		if(col+1 <3 && array[row][col+1] == ISLAND){
			System.out.println("아래 노드를 방문했는데 육지에요.");
		}

		BiFunction<Integer, Integer, Boolean> fnIndexZeroCheck_and_Island = (_row, _col)->{
			boolean result = false;

			if(_row >=0 && array[_row][_col] == ISLAND){
				result = true;
			}
			else{
				result = false;
			}

			return result;
		};

		BiFunction<Integer, Integer, Boolean> fnIndexMaxCheck_and_Island = (_row, _col)->{
			boolean result = false;

			if(_row < array.length && array[_row][_col] == ISLAND){
				result = true;
			}
			else {
				result = false;
			}

			return result;
		};

		Boolean zero_check = fnIndexZeroCheck_and_Island.apply(row - 1, col);
		System.out.println("index zero check = " + zero_check);

		Boolean max_check = fnIndexMaxCheck_and_Island.apply(row + 1, col);
		System.out.println("index max check = " + max_check);
	}

	// 또는 클래스 하나에 래핑해서 아래와 같이 할 수도 있을 것 같다.
	class MatrixUnit{
		int row;
		int col;
		String [][] array;

		public MatrixUnit(int row, int col, String[][] array){
			this.row = row;
			this.col = col;
			this.array = array;
		}
	}

	public static void main(String [] args){
		String [][] array = {
			{"1", "1", "0", "0", "0"},
			{"1", "1", "0", "0", "1"},
			{"1", "0", "0", "0", "1"}
		};
		int row_size = array.length;
		int col_size = array[0].length;

		Try1 t = new Try1();
		t.solution1(row_size, col_size, array);
	}
}

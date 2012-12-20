package main.crossover;

public enum Direction {

	LEFT_TO_RIGHT {
		@Override
		public Direction getOpposite() {
			return RIGHT_TO_LEFT;
		}

		@Override
		public String toString() {
			return "left to right";
		}
	},
	
	RIGHT_TO_LEFT {
		@Override
		public Direction getOpposite() {
			return LEFT_TO_RIGHT;
		}

		@Override
		public String toString() {
			return "right to left";
		}
	};
	
	public abstract Direction getOpposite();

	public abstract String toString();
	
}

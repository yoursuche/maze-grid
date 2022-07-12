package co.mvpfactory.maze.enums;

/**
 *
 * @author UchechukwuOnuoha
 */
public enum StepType {

    min,
    max;

    public static StepType fromString(String literal) {

        if (literal == null) {
            return null;
        }

        for (StepType step : StepType.values()) {
            if (step.name().equalsIgnoreCase(literal)) {
                return step;
            }
        }

        return null;
    }

}

package pl.edu.agh.megamud.base;

import java.util.List;
import pl.edu.agh.megamud.utils.*;

public interface Command {
	public String getName();
	public boolean interprete(Controller user, String command);
}

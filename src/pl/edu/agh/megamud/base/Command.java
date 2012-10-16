package pl.edu.agh.megamud.base;

import java.util.List;
import pl.edu.agh.megamud.utils.*;

public interface Command {
	public List<StringCommandTuple> install(InteractiveObject interactiveObject);
	public boolean interprete(User user, String command);
}

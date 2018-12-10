package application;

public class UserStory {
	String name;
	int stage;
	int priority;
	String des;

	private boolean _LOCKED_;

	public UserStory()
	{
		_LOCKED_ = false;
	}

	public boolean isLocked()
	{
		return _LOCKED_;
	}

	public boolean editStory()
	{
		if (!this.isLocked())
	{
			// TODO: Send to appropriate "editing handlers"
			return true;
	}
		else
			return false;
	}
}

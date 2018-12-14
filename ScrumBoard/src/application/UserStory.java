package application;

import java.io.Serializable;

public class UserStory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -784831226429433514L;
	/**
	 * 
	 */
	
	public String name;
	public String stage;
	public String priority;
	public String des;

	private boolean _LOCKED_;

	public UserStory(String name, String des, String stage, String priority)
	{
		this.name = name;
		this.stage = stage;
		this.des = des;
		this.priority = priority;
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

	public void setName(String nameChangeTest) {
		name = nameChangeTest;
		
	}
}

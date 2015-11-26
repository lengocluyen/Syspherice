package net.syspherice.form;

public class AnnotationInfo {
	private String username;
	private String fullname;
	private String avatar;
	private String content;
	private String createdate;
	private Boolean author;
	private String annotationid;
	
	
	
	public String getAnnotationid() {
		return annotationid;
	}

	public void setAnnotationid(String annotationid) {
		this.annotationid = annotationid;
	}

	public Boolean getAuthor() {
		return author;
	}

	public void setAuthor(Boolean author) {
		this.author = author;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
}

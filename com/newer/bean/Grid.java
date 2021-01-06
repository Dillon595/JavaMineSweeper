
package com.newer.bean;

/**  
 * @ClassName: Grid
 * @Description: 为雷区产生格子
 * @author LYL
 * @date 2021-01-04 10:06:25
*/

public class Grid {
	//格子内容属性
	private char content;
	//格子状态属性
	private boolean status;
	/**
	 * @return the content
	 */
	public char getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(char content) {
		this.content = content;
	}
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
}

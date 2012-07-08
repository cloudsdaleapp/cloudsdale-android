package org.cloudsdale.android.models.network_models;

import org.cloudsdale.android.models.Model;
import org.cloudsdale.android.models.api_models.Error;
import org.cloudsdale.android.models.api_models.Flash;

/**
 * Response bean for easy GSON (de)serialization
 * 
 * @author Jamison Greeley (atomicrat2552@gmail.com)
 */
public class Response extends Model {
	// Data objects
	protected int		status;
	protected Error[]	errors;
	protected Flash		flash;

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the errors
	 */
	public Error[] getErrors() {
		return errors;
	}

	/**
	 * @param error
	 *            the errors to set
	 */
	public void setErrors(Error[] errors) {
		this.errors = errors;
	}

	/**
	 * @return the flash
	 */
	public Flash getFlash() {
		return flash;
	}

	/**
	 * @param the
	 *            flash to set
	 */
	public void setFlash(Flash flash) {
		this.flash = flash;
	}

}

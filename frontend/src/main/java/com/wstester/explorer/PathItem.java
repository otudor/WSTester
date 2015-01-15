package com.wstester.explorer;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;

public class PathItem {
	private Path path;
	private int countNewDir = 0;


	public boolean canAccess() {
		System.out.println(this.path.toAbsolutePath().toString());
		if(Files.isWritable(FileSystems.getDefault().getPath(this.path.toAbsolutePath().toString()))){
			  return true;
			}
		return false;
	
	}
	
	public String getExtension(){
		return FilenameUtils.getExtension(this.path.toAbsolutePath().toString());
	}
	
	public boolean exists() {
		return Files.exists(this.path);
	}
	
	public boolean isDirectory(){
		return (Files.isDirectory(this.path,LinkOption.NOFOLLOW_LINKS)&& Files.exists(this.path, LinkOption.NOFOLLOW_LINKS));
	}

	public PathItem(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return path;
	}

	@Override
	public String toString() {
		System.out.println(path.toString());
		if (path.toString().compareTo("\\") == 0) {
			return "My Computer";
		}
		if ((path.getFileName() == null)) {
			return path.toString();
		} else {
			return path.getFileName().toString();
		}

	}

	public int getCountNewDir() {
		return ++this.countNewDir;
	}
}

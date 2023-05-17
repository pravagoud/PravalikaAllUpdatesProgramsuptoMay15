package com.ojas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.entity.FileUploadEntity;

@Repository
public interface FileRepository extends JpaRepository<FileUploadEntity, Integer> {

	void save(MultipartFile file);

	@Query(value = "select * from files T where TIMESTAMPDIFF(MINUTE,T.expiry_time,now()) <= ?1", nativeQuery = true)
	public List<FileUploadEntity> getAllValidFiles(long fileExpiry);

	@Query(value = "SELECT * FROM files where uploader=?1", nativeQuery = true)
	public List<FileUploadEntity> getUserFiles(String name);

	@Query(value = "SELECT * FROM fileupload.files where filetype='image/png' and uploader=?1", nativeQuery = true)
	public List<FileUploadEntity> getUserPng(String userName);

	@Query(value = "SELECT * FROM fileupload.files where filetype='audio/mpeg' and uploader=?1", nativeQuery = true)
	public List<FileUploadEntity> getAudioFiles(String userName);

	@Query(value = "SELECT * FROM fileupload.files where filetype='video/mp4' and uploader=?1", nativeQuery = true)
	public List<FileUploadEntity> getVideoFiles(String userName);

	@Query(value = "SELECT * FROM fileupload.files where filetype='application/pdf' and uploader=?1", nativeQuery = true)
	public List<FileUploadEntity> getPDFFiles(String userName);

	@Query(value = "SELECT * FROM fileupload.files where filetype='text/xml' and uploader=?1", nativeQuery = true)
	public List<FileUploadEntity> getXMLFiles(String userName);
	@Query(value = "SELECT * FROM fileupload.files where filetype='image/gif' and uploader=?1", nativeQuery = true)
	List<FileUploadEntity> getGifFiles(String userName);

}

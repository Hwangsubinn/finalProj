package kr.or.ddit.mail.vo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "mailAtchNo")
@NoArgsConstructor
public class AttatchVO implements Serializable {
	private MultipartFile mailFile;
	public AttatchVO(MultipartFile mailFile) {
		super();
		this.mailFile = mailFile;
		this.mailOrgNm = mailFile.getOriginalFilename();
		this.mailSvNm = UUID.randomUUID().toString();
		this.mailPath = mailFile.getContentType();
		this.mailAtchSize = mailFile.getSize();
		this.mailAtchFancysize = FileUtils.byteCountToDisplaySize(mailAtchSize);
	}
	private Integer mailAtchNo;
	private String mailOrgNm;
	private String mailSvNm;
	private String mailPath;
	private long mailAtchSize;
	private Integer mailNo;
	private String mailAtchFancysize;
	
	public void saveTo(File saveFolder) throws IllegalStateException, IOException {
		if(mailFile!=null)
			mailFile.transferTo(new File(saveFolder, mailSvNm));
		
	}


}

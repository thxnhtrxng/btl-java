package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Account {
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private String username;
	@NonNull
	private String password;
	private Boolean isTeacher;
}

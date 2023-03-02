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
public class Module {
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private Integer credit;
	@NonNull
	private Integer quantity;
	@NonNull
	private Integer registered;
	@NonNull
	private String description;
}

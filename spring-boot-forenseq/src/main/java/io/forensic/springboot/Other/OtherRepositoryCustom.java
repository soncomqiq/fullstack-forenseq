package io.forensic.springboot.Other;

import java.util.List;

public interface OtherRepositoryCustom{
	List<Object[]> mycustomQuery(String query);
	List<Object[]> mycustomQueryY(String query);
	List<Object[]> mycustomQueryX(String query);
}
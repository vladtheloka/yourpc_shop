package com.yourpc.dao;

import com.yourpc.entity.Billable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yourpc.entity.Item;

import java.util.Set;

public interface ItemDao extends JpaRepository<Item, Integer>
{
	Item findByName(String name);
	void deleteByName(String name);
	@Query("select distinct i from Item i left join fetch i.billable where i.id =:id")
	Item getItemWithBillables(@Param("id")int id);

	@Query("select distinct i from Item i left join fetch i.billable")
	Set<Billable> getItemWithBillables();
}

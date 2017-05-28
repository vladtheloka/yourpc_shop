package com.yourpc.serviceimpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourpc.dao.BillableDao;
import com.yourpc.entity.Billable;
import com.yourpc.entity.Item;
import com.yourpc.entity.User;
import com.yourpc.service.BillableService;

@Service
public class BillableServiceImpl implements BillableService
{
	private final BillableDao billableDao;

	@Autowired
	public BillableServiceImpl(BillableDao billableDao) {
		this.billableDao = billableDao;
	}


	public void add(Billable billable) 
	{
		billableDao.save(billable);
	}

 
	public void delete(int id) 
	{
		billableDao.delete(id);
	}

 
	public void update(Billable billable) 
	{
		billableDao.save(billable);
	}

	public Billable getOne(int id) 
	{
		return billableDao.findOne(id);
	}

	public List<Billable> getAll() 
	{
		return billableDao.findAll();
	}

	public void addUserToBillable(User user, Billable billable) 
	{
		billable.setUser(user);
		billableDao.save(billable);
	}

	public void addBillableToItem(Item item, Billable billable) 
	{
		billable.getItem().add(item);
		billableDao.save(billable);
	}

	public Billable getBillableWithItems(int id)
	{
		return billableDao.getBillableWithItems(id);
	}

	public void removeUserFromBillable(Billable billable) 
	{
		billable.setUser(null);
		billableDao.save(billable);
	}


	public Billable findByName(String name) 
	{
		return billableDao.findByName(name);
	}


	public void deleteByName(String name)
	{
		billableDao.deleteByName(name);
	}

	public Set<Item> getBillableWithItems()
	{
		return billableDao.getBillableWithItems();
	}
}

package com.eventuate.example.einventory.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventuate.example.constant.MessageConstant;
import com.eventuate.example.einventory.service.IInventoryService;
import com.eventuate.example.entity.Transaction;
import com.eventuate.example.exception.BusinessLogicException;
import com.eventuate.example.info.command.TransactionCommand;
import com.eventuate.example.info.event.ConfirmTransactionEvent;
import com.eventuate.example.utils.JsonUtils;

import io.eventuate.sync.AggregateRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IInventoryServiceImpl implements IInventoryService {
	
	private final AggregateRepository<Transaction, TransactionCommand> transactionRepository;

	@Autowired
	public IInventoryServiceImpl(AggregateRepository<Transaction, TransactionCommand> transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public boolean checkInventory(ConfirmTransactionEvent event) {
		
		log.info("Check inventory ={}",JsonUtils.objectToString(event));
		
		Transaction trans = transactionRepository.find(event.getId()).getEntity();
		if (trans == null) 
			throw new BusinessLogicException(MessageConstant.NOT_FOUND, MessageConstant.NOT_FOUND);
		Random random = new Random();
	    int val = random.nextInt(10);
	    if (val % 2 == 0) return true;
		return false;
	}

}
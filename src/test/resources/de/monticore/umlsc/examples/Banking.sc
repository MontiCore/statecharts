package de.monticore.umlsc.examples;

statechart Auftrag {

	initial state Offer
	state Production
	state Shipping
	state Payment
	final state Complete
	final state Cancelled

	// Regular flow
	Offer		-> Production   customer.sendConfirmation() / {orderParts();}
	Production 	-> Shipping		[allItemsProduced()] shipItems()
    Shipping	-> Payment		[allItemsSent()] / {customer.sendInvoice(sum);}
	Payment		-> Complete		[paid]

	// Order cancellation
	Offer 		-> Cancelled	cancel()
	Production	-> Cancelled	cancel() / {customer.sendInvoice(calculateCompensation());}
	Shipping	-> Cancelled	cancel() / {customer.sendInvoice(calculateCompensation());}
}

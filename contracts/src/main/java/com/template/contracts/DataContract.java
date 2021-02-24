package com.template.contracts;

import com.template.states.DataState;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.CommandWithParties;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;

// ************
// * Contract *
// ************
public class DataContract implements Contract {
    // This is used to identify our contract when building a transaction.
    public static final String ID = "com.template.contracts.DataContract";


    @Override
    public void verify(LedgerTransaction tx) {
    }

    public interface Commands extends CommandData {
        class Create implements Commands {}
    }
}
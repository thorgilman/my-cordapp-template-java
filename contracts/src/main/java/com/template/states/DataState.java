package com.template.states;

import com.template.contracts.DataContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.List;

// *********
// * State *
// *********
@BelongsToContract(DataContract.class)
public class DataState implements ContractState {

    //private variables
    private String data;
    private Party sourceParty;
    private Party destParty;

    /* Constructor of your Corda state */
    public DataState(String data, Party sourceParty, Party destParty) {
        this.data = data;
        this.sourceParty = sourceParty;
        this.destParty = destParty;
    }

    //getters
    public String getData() { return data; }
    public Party getSourceParty() { return sourceParty; }
    public Party getDestParty() { return destParty; }

    /* This method will indicate who are the participants and required signers when
     * this state is used in a transaction. */
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(sourceParty,destParty);
    }
}
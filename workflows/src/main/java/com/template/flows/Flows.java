package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.DataContract;
import com.template.states.DataState;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;

import java.util.Arrays;
import java.util.List;

public class Flows {

    @InitiatingFlow
    @StartableByRPC
    public static class InitiatorFlow extends FlowLogic<SignedTransaction> {

        private String dataString;
        private Party destParty;

        public InitiatorFlow(String dataString, Party destParty) {
            this.dataString = dataString;
            this.destParty = destParty;
        }

        // This is a mock function to prevent errors. Delete the body of the function before starting development.
        public SignedTransaction call() throws FlowException {
            final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

            final DataState dataState = new DataState(dataString, getOurIdentity(), destParty);

            final TransactionBuilder txBuilder = new TransactionBuilder(notary);
            txBuilder.addOutputState(dataState);
            txBuilder.addCommand(new DataContract.Commands.Create(), getOurIdentity().getOwningKey());

            final SignedTransaction ptx = getServiceHub().signInitialTransaction(txBuilder);
            final List<FlowSession> sessions = Arrays.asList(initiateFlow(getOurIdentity()));
            return subFlow(new FinalityFlow(ptx, sessions));
        }
    }


    @InitiatedBy(Flows.InitiatorFlow.class)
    public static class ResponderFlow extends FlowLogic<SignedTransaction>{
        private final FlowSession flowSession;

        public ResponderFlow(FlowSession flowSession){
            this.flowSession = flowSession;
        }

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {

            return subFlow(new ReceiveFinalityFlow(flowSession));
        }
    }
}
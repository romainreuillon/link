pragma solidity ^0.4.3;


contract DataWards {

  struct FundingProposal {
  string proposal;
  uint bounty;
  }

  mapping(address => FundingProposal[]) fundingProposals;

  function DataWards(){

  }

  function propose(string proposal, uint bounty) {
    fundingProposals[msg.sender].push(FundingProposal(proposal, bounty));
  }

  function getNb() returns (uint) {
    return fundingProposals[msg.sender].length;
  }


}
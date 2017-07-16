pragma solidity ^0.4.3;


contract DataWards {



//  struct FundingProposal {
//
//  }
//
//  mapping(string => FundingProposal) fundingProposals;

  uint nb = 0;

  function DataWards(){

  }

  function propose() Boolean {
    nb += 1;
    return true;
  }

  function getNb() Int {
    return nb;
  }

}

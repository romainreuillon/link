pragma solidity ^0.4.3;

contract DataWards {

  function DataWards(){}

  struct proposal {
    uint keyIndex;
    uint bounty;
    mapping(address => uint) funders;
  }

  struct proposals {
    string[] keys;
    mapping(string => proposal) proposals;
  }


  //using itmap for itmap.itmap;
  // Declare an iterable mapping
  proposals fundingProposals;

  function propose(string proposalAddress, uint amount) returns (bool inserted) {
    proposal storage p = fundingProposals.proposals[proposalAddress];
    p.funders[msg.sender] += amount;
    p.bounty += amount;

    if (p.keyIndex > 0) {
      return false;
    } else {
      p.keyIndex = ++fundingProposals.keys.length;
      fundingProposals.keys[p.keyIndex - 1] = proposalAddress;
      return true;
    }
  }

  function getProposalSize() constant returns (uint) {
    return fundingProposals.keys.length;
  }

  function getProposal(uint idx) constant returns (string) {
    return fundingProposals.keys[idx];
  }

  function getBounty(string proposal) constant returns (uint) {
    return fundingProposals.proposals[proposal].bounty;
  }


  // function getProposals(proposals.itmap self)  public constant returns (proposal[]) {
  //     proposal[] ret;

  //     uint nbAddresses = proposals.addressSize(self);
  //     for(uint addIdx = 0; addIdx < nbAddresses; addIdx++) {
  //         address add = proposals.getAddress(self, addIdx);
  //         uint nbProposals = proposals.proposalsSize(self, add);
  //         for(uint propIdx = 0; propIdx < nbProposals; nbProposals++) {
  //             bytes memory proposalAdd = proposals.getProposal(self, add, propIdx);
  //             proposal memory p = proposals.proposal(add, proposalAdd, proposals.getBounty(self, add, proposalAdd));
  //             ret.push(p);
  //         }
  //     }

  //     return ret;
  // }

}
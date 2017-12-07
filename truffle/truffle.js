module.exports = {
  networks: {
    development: {
      host: "localhost",
      port: 8545,
      gas: 4712388, // from web3.eth.getBlock("pending").gasLimit
      network_id: "*" // Match any network id
    },
    rinkeby: {
      host: "localhost", // Connect to geth on the specified
      port: 8545,
      from: "0xfca4c88f2e7c4567c0b7a40e0d5785800cd0c9de", // default address to use for any transaction Truffle makes during migrations
      network_id: 4,
      gas: 4612388 // Gas limit used for deploys
    }
  }
};

var fs = require('fs');
const request = require('request');
//const ifac_url = "https://testapi.io/api/ednardomartins/ifac/produtos";

const percentageRate = 1.95;

// Assigns price forcing to two decimal places and applies rate
const setPrice = (price, rate = 0) => {
 if (rate !== 0)
  price += (price * rate) / 100;
 return price.toFixed(2);
}

// Fills the master node object of a product with main info
const fillMainObj = (item) => {
 return {
  "product": {
   "name": item.nome,
   "referenceCode": item.codigoReferencia,
   "stockControl": "1",
   "eanCode": item.eanCode,
   "ipi": 10000,
  }
 };
}

// Fills the Variant node of a product object
const fillVariantObj = (variantItem) => {
 let b2bPrice = setPrice(variantItem.listaPreco[0].preco);
 let b2cPrice = setPrice(variantItem.listaPreco[1].preco, percentageRate);

 return {
  "referenceCode": variantItem.codigoReferencia,
  "presentation": variantItem.apresentacao,
  "price": b2cPrice, // nesse campo havia ambiguidade, a definição de uso de valor para B2C foi passada pelo Denis.
  "master": 1,
  "stock": {
   "quantity": variantItem.estoqueQuantidade
  },
  "priceList": [
   {
    "price": b2cPrice,
    "criteria": "B2C"
   },
   {
    "price": b2bPrice,
    "criteria": "B2B"
   }
  ]
 };
}

const loopAtVariants = (variantsObj) => {
 let newVariant = [];
 for (let x = 0; x < variantsObj.length; x++) {
  const variantItem = variantsObj[x];
  const variantObj = fillVariantObj(variantItem);
  newVariant.push(variantObj);
 }
 return newVariant;
}

const getMockData = () => {
 const jsonMock = fs.readFileSync("ifac_produtos.json", 'utf8');
 //return "a";
 return JSON.parse(jsonMock);
}

const mainProcess = () => {
 const mainObj = [];
 const ifacProdObj = getMockData()
 for (let i = 0; i < ifacProdObj.length; i++) {
  const item = ifacProdObj[i];

  const mainItemObj = fillMainObj(item);
  const variantsObj = loopAtVariants(item.variantes);

  mainItemObj.variants = variantsObj;
  mainObj.push(mainItemObj);
 }
 const mappedJson = JSON.stringify(mainObj);
 //console.log(mappedJson);
 return mappedJson;
}

const sendData = async (body) => {
 var http_req = {
  method: 'PUT',
  uri: 'https://testapi.io/api/ednardomartins/robloxy/produto',
  body: body
 };
 const res = await new Promise(
  (resolve, reject) => {
   request(
    http_req,
    (err, res, body) => {
     resolve(body);
    }
   )
  }
 ).catch(
  (rej) => {
   return rej;
  }
 ).then((body) => {
  return body
 });

 return res;
};

exports.mainProcess = mainProcess;
exports.setPrice = setPrice;
exports.fillMainObj = fillMainObj;
exports.fillVariantObj = fillVariantObj;
exports.loopAtVariants = loopAtVariants;
exports.getMockData = getMockData;
exports.mainProcess = mainProcess;
exports.sendData = sendData;
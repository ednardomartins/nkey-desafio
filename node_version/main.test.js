const index = require('./index');
const json = index.getMockData();

describe('Get mock file', () => {
 test("JSON obtained", () => {
  expect(typeof (json) === 'object' ? true : false).toBe(true);
  expect(json[0]).toHaveProperty('nome');
  expect(json[0]).toHaveProperty('categoria');
  expect(json[0]).toHaveProperty('codigoReferencia');
  expect(json[0]).toHaveProperty('eanCode');
  expect(json[0]).toHaveProperty('variantes');
 });
});

const mainObj = index.fillMainObj(json[0]);
const variantObj = index.fillVariantObj(...json[0].variantes);
describe('Filling of the object', () => {
 test("Filling of main info", () => {
  expect(mainObj).toHaveProperty('product');
 });

 test("Filling of info tags of the variants ", () => {
  expect(variantObj).toHaveProperty('referenceCode');
  expect(variantObj).toHaveProperty('presentation');
  expect(variantObj).toHaveProperty('price');
  expect(variantObj).toHaveProperty('master');
  expect(variantObj).toHaveProperty('stock');
  expect(variantObj).toHaveProperty('priceList');
 });
});

const mainExecution = index.mainProcess();
describe('Generate mapped JSON', () => {
 const jsonObj = JSON.parse(mainExecution);
 test("Veryfing if is an object", () => {
  expect(typeof jsonObj === 'object' ? true : false).toBe(true);
 });

 const item = jsonObj[0];
 test("Object contains main fields", () => {
  expect(item).toHaveProperty('product');
  expect(item).toHaveProperty('variants');
 });

 test("Object contains 'product' child fields", () => {
  const child = item.product;
  expect(child).toHaveProperty('eanCode');
  expect(child).toHaveProperty('ipi');
  expect(child).toHaveProperty('name');
  expect(child).toHaveProperty('referenceCode');
  expect(child).toHaveProperty('stockControl');
 });

 test("Object contains 'variants' child fields", () => {
  let child = item.variants[0];
  expect(child).toHaveProperty('master');
  expect(child).toHaveProperty('presentation');
  expect(child).toHaveProperty('price');
  expect(child).toHaveProperty('priceList');
  expect(child).toHaveProperty('referenceCode');
  expect(child).toHaveProperty('stock');
 });
})

describe('Sending mapped JSON', () => {
 test('Send JSON', async () => {
  expect(await index.sendData(mainExecution)).toBe("sucesso!!");
 });

});

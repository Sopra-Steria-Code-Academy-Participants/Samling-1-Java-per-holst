// @ts-check

// se også playwright.config.ts

import { test, expect, request } from '@playwright/test';

test('API works', async ({ page,
  context,
  browser,
  playwright }) => {
  const request = await playwright.request.newContext();

  const issues = await request.get('/v1/hello/world');
  console.log(issues);

  expect(issues.ok()).toBeTruthy();
  expect(await issues.text()).toBe("Hello .net noobs!!");

});

test('no post API works', async ({ page,
  context,
  browser,
  playwright }) => {
  const request = await playwright.request.newContext();

  const issues = await request.post('/v1/hello/world', {
    data: {
      hello: `it's me`
    }
  });

  expect(issues.status().toString()).toMatch("405");
  expect(await issues.text()).toContain(`"Method Not Allowed"`);

});

test('valid APIs', async ({ page,
  context,
  browser,
  playwright }) => {
  const request = await playwright.request.newContext();

  const issues = await request.head('/v1/hello/world');

  expect.soft(issues.ok()).toBeTruthy();
  expect.soft(issues.status().toString()).toMatch("200");
  expect.soft(await issues.text()).toBe(``);

});


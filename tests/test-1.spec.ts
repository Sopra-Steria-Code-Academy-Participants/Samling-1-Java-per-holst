import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://kagensdag.nemtilmeld.dk/');
  await page.getByRole('link', { name: 'Forside' }).click();
  await page.getByRole('button', { name: 'Læs mere' }).click();
  await page.locator('#reservation_action').click();
  await page.getByRole('button', { name: 'Slet' }).click();
  await expect(page.locator('#main_content')).toMatchAriaSnapshot(`- text: /6\\. maj \\d+/`);
});
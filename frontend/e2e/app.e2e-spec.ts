import { OgamecloneFrontendPage } from './app.po';

describe('ogameclone-frontend App', () => {
  let page: OgamecloneFrontendPage;

  beforeEach(() => {
    page = new OgamecloneFrontendPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});

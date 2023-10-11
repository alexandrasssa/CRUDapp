import { useState } from 'react';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';

import KanbanBoard from './components/KanbanBoard';
import { ModalProvider } from './hooks/useModalTask';
import store from './store';
import GlobalStyle from './styles/global';
import lightTheme from './styles/themes/light';
import { ModalFeatureProvider } from './hooks/useModalFeature';

function App() {
  const [theme] = useState(lightTheme);

  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
        <ModalProvider>
          <ModalFeatureProvider>
            <div className="App">
              <GlobalStyle />
              <KanbanBoard />
            </div>
          </ModalFeatureProvider>

        </ModalProvider>
      </ThemeProvider>
    </Provider>
      );
}

export default App;

import React, { createContext, ReactNode, useContext, useState } from 'react';
import ICardFeature from '../interfaces/ICardFeature';

interface ModalFeatureContextData {
  visible: boolean;
  toggleFeatureVisibility: (card: ICardFeature | undefined) => void;
  selectedCard: ICardFeature | undefined;
}

interface ModalFeatureProviderProps {
  children: ReactNode;
}

const ModalFeatureContext = createContext<ModalFeatureContextData>({} as ModalFeatureContextData);

const ModalFeatureProvider: React.FC<ModalFeatureProviderProps> = ({ children }) => {
  const [visible, setVisible] = useState<boolean>(false);
  const [selectedCard, setSelectedCard] = useState<ICardFeature | undefined>();

  const toggleFeatureVisibility = (card: ICardFeature | undefined) => {
    if (card) setSelectedCard(card);
    else setSelectedCard(undefined);
    setVisible(!visible);
  }
  return (
    <ModalFeatureContext.Provider value={{ visible, toggleFeatureVisibility, selectedCard }}>
      {children}
    </ModalFeatureContext.Provider>
  );
};

function useModalFeature(): ModalFeatureContextData {
  const context = useContext(ModalFeatureContext);

  if (!context) {
    throw new Error('useModal must be used within a ModalProvider');
  }
  return context;
}

export { ModalFeatureProvider, useModalFeature };

import React from 'react';
import { useModalFeature } from '../../hooks/useModalFeature';

import { Container } from './styles';

export const ButtonAddCardFeature: React.FC = () => {
    const { toggleFeatureVisibility } = useModalFeature();

    const handleOpenModal = () => {
        toggleFeatureVisibility(undefined)
    }

  return (
    <Container onClick={handleOpenModal}>
        <strong>+ Add feature</strong>
    </Container>
  );
}
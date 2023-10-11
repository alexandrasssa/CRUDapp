import React from 'react';
import { useModalTask } from '../../hooks/useModalTask';

import { Container } from './styles';

export const ButtonAddCardTask: React.FC = () => {
    const { toggleVisibility } = useModalTask();

    const handleOpenModal = () => {
        toggleVisibility(undefined)
    }

  return (
    <Container onClick={handleOpenModal}>
        <strong>+ Add Card</strong>
    </Container>
  );
}
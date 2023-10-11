import React, { useContext } from 'react';

import { Draggable } from 'react-beautiful-dnd';
import { ThemeContext } from 'styled-components';

import { useModalTask } from '../../hooks/useModalTask';
import ICard from '../../interfaces/ICard';

import { CardBorder, CardBottom, CardContainer } from './styles';

interface CardProps {
  card: ICard;
  index: number;
}

const Card: React.FC<CardProps> = ({ card, index }) => {
  const theme = useContext(ThemeContext); 

  const { toggleVisibility } = useModalTask();

  return (
    <Draggable draggableId={card.id.toString()} index={index}>
      {provided => (
        <CardContainer 
          onClick={() => toggleVisibility(card)} 
          ref={provided.innerRef} 
          {...provided.draggableProps} 
          {...provided.dragHandleProps}
        >
          <CardBorder color={theme.colors.primary}/> 
          <h3>{card.title}</h3>
          <CardBottom>
            <p>+ View More</p> 
          </CardBottom>
        </CardContainer>
      )}
    </Draggable>
  )
}

export default Card;
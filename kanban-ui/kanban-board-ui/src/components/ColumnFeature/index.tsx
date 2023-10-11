import React from 'react';

import { Droppable } from 'react-beautiful-dnd';

import { CardsList, Container } from './styles';
import CardFeature from '../CardFeature';
import ICardFeature from '../../interfaces/ICardFeature';

interface ColumnFeatureProps {
  cards: ICardFeature[];
  index: number;
}

const Column: React.FC<ColumnFeatureProps> = ({ cards, index }) => {
  return (
    <Container isFirstColumn={index === 0}>
      <h2>Features</h2>
      <Droppable droppableId={'Feature'}>
        {(provided) => (
          <CardsList ref={provided.innerRef} {...provided.droppableProps}>
            {cards
              .map((card, index) => <CardFeature key={card.id} card={card} index={index}/>)
            }
            {provided.placeholder}
        </CardsList>
        )}
        </Droppable>
    </Container>
  )
}

export default Column;
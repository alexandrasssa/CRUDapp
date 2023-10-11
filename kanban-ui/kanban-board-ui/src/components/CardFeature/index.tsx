import React, { useContext, useEffect, useState } from 'react';

import { Draggable } from 'react-beautiful-dnd';
import { ThemeContext } from 'styled-components';

import getCategoryBackgroundColor from '../../helpers/getCategoryBackgroundColor';
import { useModalFeature } from '../../hooks/useModalFeature';
import ICardFeature from '../../interfaces/ICardFeature';
import Badge from '../Badge';

import { CardBorder, CardBottom, CardContainer } from './styles';

interface CardProps {
  card: ICardFeature;
  index: number;
}

const CardFeature: React.FC<CardProps> = ({ card, index }) => {
  const theme = useContext(ThemeContext); 

  const [backgroundColor, setBackgroundColor] = useState<string>(theme.colors.primary);

  const { toggleFeatureVisibility } = useModalFeature();

  useEffect(() => {
    if (card) {
      const categoryColor = getCategoryBackgroundColor(theme, card.category);
      setBackgroundColor(categoryColor);
    }
  }, [card])

  return (
    <Draggable draggableId={card.id.toString()} index={index}>
      {provided => (
        <CardContainer 
          onClick={() => toggleFeatureVisibility(card)} 
          hideCard={false}
          ref={provided.innerRef} 
          {...provided.draggableProps} 
          {...provided.dragHandleProps}
        >
          <CardBorder color={backgroundColor}/> 
          <h3>{card.title}</h3>
          <CardBottom>
            <Badge category={card.category}/>
            <p>+ View feature details</p>
          </CardBottom>
        </CardContainer>
      )}
    </Draggable>
  )
}

export default CardFeature;
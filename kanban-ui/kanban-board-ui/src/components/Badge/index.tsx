import React, { useContext } from 'react';
import { ThemeContext } from 'styled-components';

import ICategory from '../../interfaces/ICategory';
import { BadgeContainer } from './styles';

interface BadgeProps {
  category: ICategory
}

const Badge: React.FC<BadgeProps> = ({ category }) => {
  const theme = useContext(ThemeContext); 
 
  return ( 
    <BadgeContainer color={theme.colors.primary}>
      <p>{category}</p>
    </BadgeContainer>
  )
}

export default Badge;
import styled from "styled-components";
import { SCREEN_BREAKPOINTS } from "../../constants/breakpoints";

interface LabelContainerProps {
  color: any;
}

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 2rem 0 2rem 0;
  position: relative;

  @media(max-width: ${SCREEN_BREAKPOINTS.EXTRA_LARGE}px) {
    padding: 2rem 0 2rem 0;
  }
`

export const ContainerFeatures = styled.div`
  display: flex;
  flex-direction: column;
  height: 30%;
  width: 100%;
  padding: 2rem 0 2rem 0;
  position: relative;

  @media(max-width: ${SCREEN_BREAKPOINTS.EXTRA_LARGE}px) {
    padding: 2rem 0 2rem 0;
  }
`

export const StatusesColumnsContainer = styled.div`
  padding-top: 1rem;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 1.5rem;
  max-width: 100vw;
  // height: 25rem;
  overflow-x: auto;
`

export const LabelContainer = styled.div<LabelContainerProps>`
  padding: 5px;
  background-color: ${({color}) => color};
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content:space-between ;
  border-radius: 5px;
  cursor: pointer;

  label{
    font-weight: bold;
    color: #fff;
    padding-left: 5px;
    text-transform: uppercase;
    cursor: pointer;
  }
`

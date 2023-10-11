import IColumn from "../interfaces/IColumn";
import IStatus from "../interfaces/IStatus";

const mockColumns: IColumn[] = [
  {
    id: IStatus.BACKLOG,
    title: 'Backlog',
  },
  {
    id: IStatus.TO_DO,
    title: 'To Do',
  },
  {
    id: IStatus.DOING,
    title: 'Doing',
  },
  {
    id: IStatus.IN_REVIEW,
    title: 'In Review',
  },
  {
    id: IStatus.DONE,
    title: 'Done',
  }
]

export default mockColumns;
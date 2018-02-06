import { BaseEntity } from './../../shared';

export class CategoriaFinapp implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string
    ) {
    }
}

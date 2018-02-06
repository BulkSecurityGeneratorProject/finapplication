import { BaseEntity } from './../../shared';

export class TipoContaFinapp implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string
    ) {
    }
}

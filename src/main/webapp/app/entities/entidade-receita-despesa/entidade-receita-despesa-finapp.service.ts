import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { EntidadeReceitaDespesaFinapp } from './entidade-receita-despesa-finapp.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EntidadeReceitaDespesaFinappService {

    private resourceUrl = SERVER_API_URL + 'api/entidade-receita-despesas';

    constructor(private http: Http) { }

    create(entidadeReceitaDespesa: EntidadeReceitaDespesaFinapp): Observable<EntidadeReceitaDespesaFinapp> {
        const copy = this.convert(entidadeReceitaDespesa);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(entidadeReceitaDespesa: EntidadeReceitaDespesaFinapp): Observable<EntidadeReceitaDespesaFinapp> {
        const copy = this.convert(entidadeReceitaDespesa);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<EntidadeReceitaDespesaFinapp> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(entidadeReceitaDespesa: EntidadeReceitaDespesaFinapp): EntidadeReceitaDespesaFinapp {
        const copy: EntidadeReceitaDespesaFinapp = Object.assign({}, entidadeReceitaDespesa);
        return copy;
    }
}

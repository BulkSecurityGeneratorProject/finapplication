/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FinapplicationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TipoContaFinappDetailComponent } from '../../../../../../main/webapp/app/entities/tipo-conta/tipo-conta-finapp-detail.component';
import { TipoContaFinappService } from '../../../../../../main/webapp/app/entities/tipo-conta/tipo-conta-finapp.service';
import { TipoContaFinapp } from '../../../../../../main/webapp/app/entities/tipo-conta/tipo-conta-finapp.model';

describe('Component Tests', () => {

    describe('TipoContaFinapp Management Detail Component', () => {
        let comp: TipoContaFinappDetailComponent;
        let fixture: ComponentFixture<TipoContaFinappDetailComponent>;
        let service: TipoContaFinappService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinapplicationTestModule],
                declarations: [TipoContaFinappDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TipoContaFinappService,
                    JhiEventManager
                ]
            }).overrideTemplate(TipoContaFinappDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoContaFinappDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoContaFinappService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TipoContaFinapp(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tipoConta).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

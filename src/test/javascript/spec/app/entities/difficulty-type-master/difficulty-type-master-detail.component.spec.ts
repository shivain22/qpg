import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { DifficultyTypeMasterDetailComponent } from 'app/entities/difficulty-type-master/difficulty-type-master-detail.component';
import { DifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';

describe('Component Tests', () => {
  describe('DifficultyTypeMaster Management Detail Component', () => {
    let comp: DifficultyTypeMasterDetailComponent;
    let fixture: ComponentFixture<DifficultyTypeMasterDetailComponent>;
    const route = ({ data: of({ difficultyTypeMaster: new DifficultyTypeMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [DifficultyTypeMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DifficultyTypeMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DifficultyTypeMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load difficultyTypeMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.difficultyTypeMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

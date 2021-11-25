package ru.jegensomme.homeaccountant.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jegensomme.homeaccountant.Identified;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseTo implements Identified {
    protected Integer id;
}
